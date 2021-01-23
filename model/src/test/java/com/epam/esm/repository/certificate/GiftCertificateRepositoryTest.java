package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.tag.TagMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class GiftCertificateRepositoryTest {
    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateRepository giftCertificateRepository;
    private static final String CREATE_TABLE_QUERY_FILENAME = "schema.sql";
    private GiftCertificateMapper giftCertificateMapper;
    private TagMapper tagMapper;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScript(CREATE_TABLE_QUERY_FILENAME).setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateMapper = new GiftCertificateMapper();
        tagMapper = new TagMapper();
        giftCertificateRepository = new GiftCertificateRepository(jdbcTemplate, giftCertificateMapper, tagMapper);
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void readAll_returnsTheExpectedResult() {
        // given
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        LocalDateTime createDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        LocalDateTime lastUpdateDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        LocalDateTime createDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
        LocalDateTime lastUpdateDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
        expectedGiftCertificates.add(new GiftCertificate(1, "name1", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        expectedGiftCertificates.add(new GiftCertificate(2, "name2", "description2", new BigDecimal(22), 22, createDateSecondGiftCertificate, lastUpdateDateSecondGiftCertificate, null));
        // when
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.readAll();
        // then
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void readAll_theActualValueNotSimilarToTheExpectedValue() {
        // given
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        LocalDateTime createDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        LocalDateTime lastUpdateDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        LocalDateTime createDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
        LocalDateTime lastUpdateDateSecondGiftCertificate = LocalDateTime.of(2021, 01, 22, 22, 22, 22);
        expectedGiftCertificates.add(new GiftCertificate(1, "non-existent name", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        expectedGiftCertificates.add(new GiftCertificate(2, "non-existent name2", "description2", new BigDecimal(22), 22, createDateSecondGiftCertificate, lastUpdateDateSecondGiftCertificate, null));
        // when
        List<GiftCertificate> actualGiftCertificates = giftCertificateRepository.readAll();
        // then
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void read_existId_returnsTheExpectedResult() {
        // given
        Integer giftCertificateID = 1;
        LocalDateTime createDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        LocalDateTime lastUpdateDateFirstGiftCertificate = LocalDateTime.of(2021, 01, 22, 11, 11, 11);
        Optional<GiftCertificate> expectedCertificateFirst = Optional.of(new GiftCertificate(1, "name1", "description1", new BigDecimal(11), 11, createDateFirstGiftCertificate, lastUpdateDateFirstGiftCertificate, null));
        // when
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateRepository.read(giftCertificateID);
        // then
        assertEquals(expectedCertificateFirst, actualGiftCertificate);
    }

    @Test
    void read_notExistId_returnEmptyOptional() {
        // given
        Integer giftCertificateID = 1123;
        // when
        Optional<GiftCertificate> readGiftCertificate = giftCertificateRepository.read(giftCertificateID);
        // then
        assertEquals(Optional.empty(), readGiftCertificate);
    }

    @Test
    void delete_existId_returnedNonZeroNumber() {
        // given
        Integer giftCertificateID = 1;
        // when
        Integer deleteNumber = giftCertificateRepository.delete(giftCertificateID);
        // then
        assertNotEquals(0, deleteNumber);
    }

    @Test
    void delete_notExistId_returnedZeroNumber() {
        // given
        Integer giftCertificateID = 1123;
        // when
        Integer deleteNumber = giftCertificateRepository.delete(giftCertificateID);
        // then
        assertEquals(0, deleteNumber);
    }

    @Test
    void update_existId_returnedNonZeroNumber() {
        // given
        Integer giftCertificateID = 1;
        String modifiedName = "newName";
        String notModifiedDescription = "description1";
        BigDecimal notModifiedPrice = BigDecimal.valueOf(11);
        Integer notModifiedDuration = 11;
        List<Tag> tags = new ArrayList<>();
        GiftCertificate modifiedGiftCertificate = new GiftCertificate();
        modifiedGiftCertificate.setId(giftCertificateID);
        modifiedGiftCertificate.setName(modifiedName);
        modifiedGiftCertificate.setDescription(notModifiedDescription);
        modifiedGiftCertificate.setPrice(notModifiedPrice);
        modifiedGiftCertificate.setDuration(notModifiedDuration);
        modifiedGiftCertificate.setTags(tags);
        // when
        Integer updateNumber = giftCertificateRepository.update(modifiedGiftCertificate);
        // then
        assertNotEquals(0, updateNumber);
    }

    @Test
    void update_notExistId_returnedNonZeroNumber() {
        // given
        Integer giftCertificateID = 1123;
        String modifiedName = "newName";
        String notModifiedDescription = "description1";
        BigDecimal notModifiedPrice = BigDecimal.valueOf(11);
        Integer notModifiedDuration = 11;
        List<Tag> tags = new ArrayList<>();
        GiftCertificate modifiedGiftCertificate = new GiftCertificate();
        modifiedGiftCertificate.setId(giftCertificateID);
        modifiedGiftCertificate.setName(modifiedName);
        modifiedGiftCertificate.setDescription(notModifiedDescription);
        modifiedGiftCertificate.setPrice(notModifiedPrice);
        modifiedGiftCertificate.setDuration(notModifiedDuration);
        modifiedGiftCertificate.setTags(tags);
        // when
        Integer updateNumber = giftCertificateRepository.update(modifiedGiftCertificate);
        // then
        assertEquals(0, updateNumber);
    }

    // TO DO
    @Test
    void create_returnsEntityWithID() {
        // given
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("newCertificate");
        giftCertificate.setDescription("description");
        giftCertificate.setPrice(BigDecimal.valueOf(1230));
        giftCertificate.setDuration(21);
        // when
        GiftCertificate createdGiftCertificate = giftCertificateRepository.create(giftCertificate);
        // then
        assertEquals(3,createdGiftCertificate.getId());
    }
    // TO DO
    @Test
    void joinCertificatesAndTags() {
    }
}