package com.epam.esm.repository.certificate;

import com.epam.esm.configuration.DatabaseBeanConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.NotEnoughDataForRegistrationException;
import com.epam.esm.repository.tag.TagMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseBeanConfiguration.class)
class GiftCertificateRepositoryTest {
    private static final String CREATE_TABLE_QUERY_FILENAME = "schema.sql";
    @Autowired
    private GiftCertificateMapper giftCertificateMapper;
    @Autowired
    private TagMapper tagMapper;
    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateRepository giftCertificateRepository;

    private LocalDateTime createDateFirstGiftCertificate;
    private LocalDateTime lastUpdateDateFirstGiftCertificate;
    private LocalDateTime createDateSecondGiftCertificate;
    private LocalDateTime lastUpdateDateSecondGiftCertificate;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScript(CREATE_TABLE_QUERY_FILENAME).setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateRepository = new GiftCertificateRepository(jdbcTemplate, giftCertificateMapper, tagMapper);
    }

<<<<<<< HEAD
    private void setUpDate() {
=======
    public void setUpDate() {
>>>>>>> a1b8603b1c0e7d87948fb781b0b562076f177287
        createDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        lastUpdateDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        createDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
        lastUpdateDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void readAll_returnsTheExpectedResult_test() {
        // given
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        setUpDate();
        expectedGiftCertificates.add(new GiftCertificate(1, "name1", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        expectedGiftCertificates.add(new GiftCertificate(2, "name2", "description2", new BigDecimal(22), 22, createDateSecondGiftCertificate, lastUpdateDateSecondGiftCertificate, null));
        // when
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.readAll();
        // then
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void readAll_theActualValueNotSimilarToTheExpectedValue_test() {
        // given
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        setUpDate();
        expectedGiftCertificates.add(new GiftCertificate(1, "non-existent name", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        expectedGiftCertificates.add(new GiftCertificate(2, "non-existent name2", "description2", new BigDecimal(22), 22, createDateSecondGiftCertificate, lastUpdateDateSecondGiftCertificate, null));
        // when
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.readAll();
        // then
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void read_existId_returnsTheExpectedResult_test() {
        // given
        int giftCertificateID = 1;
        setUpDate();
        Optional<GiftCertificate> expectedCertificateFirst = Optional.of(new GiftCertificate(1, "name1", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        // when
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateRepository.read(giftCertificateID);
        // then
        assertEquals(expectedCertificateFirst, actualGiftCertificate);
    }

    @Test
    void read_notExistId_returnEmptyOptional_test() {
        // given
        int giftCertificateID = 1123;
        // when
        Optional<GiftCertificate> readGiftCertificate = giftCertificateRepository.read(giftCertificateID);
        // then
        assertEquals(Optional.empty(), readGiftCertificate);
    }

    @Test
    void delete_existId_returnedNonZeroNumber_test() {
        // given
        int giftCertificateID = 1;
        // when
        int deleteNumber = giftCertificateRepository.delete(giftCertificateID);
        // then
        assertNotEquals(0, deleteNumber);
    }

    @Test
    void delete_notExistId_returnedZeroNumber_test() {
        // given
        int giftCertificateID = 1123;
        // when
        int deleteNumber = giftCertificateRepository.delete(giftCertificateID);
        // then
        assertEquals(0, deleteNumber);
    }

    @Test
    void update_existId_returnedNonZeroNumber_test() {
        // given
        int giftCertificateID = 1;
        String modifiedName = "newName";
        String notModifiedDescription = "description1";
        BigDecimal notModifiedPrice = BigDecimal.valueOf(11);
        int notModifiedDuration = 11;
        List<Tag> tags = new ArrayList<>();
        GiftCertificate modifiedGiftCertificate = new GiftCertificate();
        modifiedGiftCertificate.setId(giftCertificateID);
        modifiedGiftCertificate.setName(modifiedName);
        modifiedGiftCertificate.setDescription(notModifiedDescription);
        modifiedGiftCertificate.setPrice(notModifiedPrice);
        modifiedGiftCertificate.setDuration(notModifiedDuration);
        modifiedGiftCertificate.setTags(tags);
        // when
        int updateNumber = giftCertificateRepository.update(modifiedGiftCertificate);
        // then
        assertNotEquals(0, updateNumber);
    }

    @Test
    void update_notExistId_returnedNonZeroNumber_test() {
        // given
        int giftCertificateID = 1123;
        String modifiedName = "newName";
        String notModifiedDescription = "description1";
        BigDecimal notModifiedPrice = BigDecimal.valueOf(11);
        int notModifiedDuration = 11;
        List<Tag> tags = new ArrayList<>();
        GiftCertificate modifiedGiftCertificate = new GiftCertificate();
        modifiedGiftCertificate.setId(giftCertificateID);
        modifiedGiftCertificate.setName(modifiedName);
        modifiedGiftCertificate.setDescription(notModifiedDescription);
        modifiedGiftCertificate.setPrice(notModifiedPrice);
        modifiedGiftCertificate.setDuration(notModifiedDuration);
        modifiedGiftCertificate.setTags(tags);
        // when
        int updateNumber = giftCertificateRepository.update(modifiedGiftCertificate);
        // then
        assertEquals(0, updateNumber);
    }

    @Test
    void create_returnCreatedEntityWith_Id_CreatedDate_LastUpdateDate_test() {
        // given
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("newCertificate");
        giftCertificate.setDescription("description");
        giftCertificate.setPrice(BigDecimal.valueOf(1230));
        giftCertificate.setDuration(21);
        // when
        GiftCertificate createdGiftCertificate = giftCertificateRepository.create(giftCertificate);
        // then
        assertEquals(3, createdGiftCertificate.getId());
    }

    @Test
    void create_notAllParametersWerePassedForRegistration_thrownNotEnoughDataForRegistrationException_test() {
        // given
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setDescription("description");
        giftCertificate.setPrice(BigDecimal.valueOf(1230));
        giftCertificate.setDuration(21);
        // when
        // then
        assertThrows(NotEnoughDataForRegistrationException.class, () -> giftCertificateRepository.create(giftCertificate));
    }

    @Test
    void joinCertificatesAndTags_returnsCertificatesWithTagsAttachedToThem_test() {
        // given
        List<GiftCertificate> giftCertificateListWithoutTags = new ArrayList<>();
        setUpDate();
        giftCertificateListWithoutTags.add(new GiftCertificate(1, "name1", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        giftCertificateListWithoutTags.add(new GiftCertificate(2, "name2", "description2", new BigDecimal(22), 22, createDateSecondGiftCertificate, lastUpdateDateSecondGiftCertificate, null));
        // when
        giftCertificateRepository.joinCertificatesAndTags(giftCertificateListWithoutTags);
        // then
        for (GiftCertificate giftCertificate : giftCertificateListWithoutTags) {
            assertNotEquals(giftCertificate.getTags(), null);
        }
    }

    @Test
    void readByQueryParameters_readGiftCertificatesBy_Name_TagName_Description_test() {
        // given
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("tagName", "nameTag1");
        parameters.put("name", "");
        parameters.put("description", "");
        parameters.put("sortType", "");
        parameters.put("orderType", "");

        HashMap<String, String> parameters2 = new HashMap<>();
        parameters2.put("tagName", "");
        parameters2.put("name", "name2");
        parameters2.put("description", "");
        parameters2.put("sortType", "");
        parameters2.put("orderType", "");

        HashMap<String, String> parameters3 = new HashMap<>();
        parameters3.put("tagName", "");
        parameters3.put("name", "");
        parameters3.put("description", "description1");
        parameters3.put("sortType", "");
        parameters3.put("orderType", "");
        // when
        List<GiftCertificate> giftCertificates = giftCertificateRepository.readByQueryParameters(parameters);
        List<GiftCertificate> giftCertificates2 = giftCertificateRepository.readByQueryParameters(parameters2);
        List<GiftCertificate> giftCertificates3 = giftCertificateRepository.readByQueryParameters(parameters3);
        // then
        assertEquals("name1", giftCertificates.get(0).getName());
        assertEquals("name2", giftCertificates2.get(0).getName());
        assertEquals("name1", giftCertificates3.get(0).getName());
    }
}