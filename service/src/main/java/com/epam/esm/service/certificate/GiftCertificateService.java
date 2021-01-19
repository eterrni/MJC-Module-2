package com.epam.esm.service.certificate;

import com.epam.esm.converter.SortAndOrderConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificateQueryParameters;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.certificate.GiftCertificateRepository;
import com.epam.esm.repository.exception.DuplicateNameException;
import com.epam.esm.repository.tag.TagRepository;
import com.epam.esm.service.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateService implements GiftCertificate<GiftCertificateDto, Integer> {

    private static final String EMPTY_VALUE = "";

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    private TagRepository tagDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GiftCertificateDto> readAll() {
        List<com.epam.esm.entity.GiftCertificate> giftCertificates = giftCertificateRepository.readAll();
        giftCertificateRepository.joinCertificatesAndTags(giftCertificates);
        return giftCertificates.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto read(final Integer id) {
        Optional<com.epam.esm.entity.GiftCertificate> readGiftCertificate = giftCertificateRepository.read(id);
        if (readGiftCertificate.isPresent()) {
            com.epam.esm.entity.GiftCertificate giftCertificate = readGiftCertificate.get();
            giftCertificateRepository.joinCertificatesAndTags(Collections.singletonList((giftCertificate)));
            return modelMapper.map(giftCertificate, GiftCertificateDto.class);
        } else {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        }
    }

    @Override
    public List<GiftCertificateDto> readByQueryParameters(String tagName, String name, String description, String sortType, String orderType) {
        if (isAllQueryParametersNull(tagName, name, description, sortType, orderType)) {
            return giftCertificateRepository.readAll().stream().map(this::joinGiftCertificateAndTags).collect(Collectors.toList());
        } else {
            String orderTypeConverter = SortAndOrderConverter.orderTypeConverter(orderType);
            String sortTypeConverter = SortAndOrderConverter.sortTypeConverter(sortType);
            HashMap<String, String> parametersMap = new HashMap<>();
            parametersMap.put("tagName", tagName);
            parametersMap.put("name", name);
            parametersMap.put("description", description);
            parametersMap.put("sortType", sortTypeConverter);
            parametersMap.put("orderType", orderTypeConverter);
            prepareParametersForRequest(parametersMap);
            return giftCertificateRepository.readByQueryParameters(parametersMap).stream().map(this::joinGiftCertificateAndTags).collect(Collectors.toList());
        }
    }


    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        com.epam.esm.entity.GiftCertificate createdGiftCertificate;
        createAndSetTags(giftCertificateDto);
        createdGiftCertificate = (com.epam.esm.entity.GiftCertificate) giftCertificateRepository.create(modelMapper.map(giftCertificateDto, com.epam.esm.entity.GiftCertificate.class));
        return modelMapper.map(createdGiftCertificate, GiftCertificateDto.class);
    }

    @Override
    @Transactional
    public void update(GiftCertificateDto modifiedGiftCertificateDto) {
        GiftCertificateDto readGiftCertificateDto = read(modifiedGiftCertificateDto.getId());
        if (readGiftCertificateDto == null) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + modifiedGiftCertificateDto.getId() + " in Database");
        }
        com.epam.esm.entity.GiftCertificate readGiftCertificate = modelMapper.map(readGiftCertificateDto, com.epam.esm.entity.GiftCertificate.class);
        com.epam.esm.entity.GiftCertificate modifiedGiftCertificate = modelMapper.map(modifiedGiftCertificateDto, com.epam.esm.entity.GiftCertificate.class);
        updateGiftCertificateFields(readGiftCertificate, modifiedGiftCertificate);
        giftCertificateRepository.update(readGiftCertificate);
    }

    @Override
    @Transactional
    public void delete(final Integer id) {
        if (giftCertificateRepository.delete(id) == 0) {
            throw new NotExistIdEntityException("There is no gift certificate with ID = " + id + " in Database");
        }
    }

    private GiftCertificateDto joinGiftCertificateAndTags(com.epam.esm.entity.GiftCertificate giftCertificate) {
        giftCertificateRepository.joinCertificatesAndTags(Collections.singletonList(giftCertificate));
        return modelMapper.map(giftCertificate, GiftCertificateDto.class);
    }

    private void prepareParametersForRequest(HashMap<String, String> parameters) {
        if (Objects.isNull(parameters.get("tagName"))) {
            parameters.put("tagName", EMPTY_VALUE);
        }
        if (Objects.isNull(parameters.get("name"))) {
            parameters.put("name", EMPTY_VALUE);
        }
        if (Objects.isNull(parameters.get("description"))) {
            parameters.put("description", EMPTY_VALUE);
        }
        if (Objects.isNull(parameters.get("sortType"))) {
            parameters.put("sortType", GiftCertificateQueryParameters.SortType.DEFAULT.getSortType());
            parameters.put("orderType", GiftCertificateQueryParameters.OrderType.DEFAULT.getOrderType());
        } else {
            if (Objects.isNull(parameters.get("orderType"))) {
                parameters.put("orderType", GiftCertificateQueryParameters.OrderType.DEFAULT.getOrderType());
            }
        }
    }

    private boolean isAllQueryParametersNull(String... parameters) {
        return Arrays.stream(parameters).allMatch(Objects::isNull);
    }

    private void createAndSetTags(GiftCertificateDto giftCertificateDto) {
        List<TagDto> tags = new ArrayList<>();
        if (giftCertificateDto.getTags() != null) {
            TagDto add;
            for (TagDto tagDto : giftCertificateDto.getTags()) {
                Tag tag = modelMapper.map(tagDto, Tag.class);
                try {
                    add = modelMapper.map(tagDAO.create(tag), TagDto.class);
                    tags.add(add);
                } catch (DuplicateNameException tagExist) {
                    add = modelMapper.map(tagDAO.readTagByName(tag.getName()), TagDto.class);
                    tags.add(add);
                }
            }
            giftCertificateDto.setTags(tags);
        }

    }

    private void updateGiftCertificateFields(com.epam.esm.entity.GiftCertificate readGiftCertificate, com.epam.esm.entity.GiftCertificate
            modifiedGiftCertificate) {
        if (Objects.nonNull((modifiedGiftCertificate.getDuration()))) {
            readGiftCertificate.setDuration(modifiedGiftCertificate.getDuration());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getDescription())) {
            readGiftCertificate.setDescription(modifiedGiftCertificate.getDescription());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getName())) {
            readGiftCertificate.setName(modifiedGiftCertificate.getName());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getPrice())) {
            readGiftCertificate.setPrice(modifiedGiftCertificate.getPrice());
        }
        if (Objects.nonNull(modifiedGiftCertificate.getTags())) {
            GiftCertificateDto modifiedGiftCertificateDto = modelMapper.map(modifiedGiftCertificate, GiftCertificateDto.class);
            createAndSetTags(modifiedGiftCertificateDto);
            readGiftCertificate.setTags(modelMapper.map(modifiedGiftCertificateDto, com.epam.esm.entity.GiftCertificate.class).getTags());
        }
    }
}