package by.makedon.selectioncommittee.databasepopulator.entity.factory;

import by.makedon.selectioncommittee.databasepopulator.dao.SpecialityDao;
import by.makedon.selectioncommittee.databasepopulator.dao.impl.SpecialityDaoImpl;
import by.makedon.selectioncommittee.databasepopulator.entity.Enrollee;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author Yahor Makedon
 */
public final class EnrolleeFactory {
    private static final EnrolleeFactory instance = new EnrolleeFactory();

    private final List<Integer> specialityIds;
    private final Map<Subject, Consumer<Enrollee.EnrolleeBuilder>> subjectToConsumerBuilderSetUpMap;

    private EnrolleeFactory() {
        SpecialityDao specialityDao = new SpecialityDaoImpl();
        specialityIds = new ArrayList<>(specialityDao.getAllSpecialityId());

        subjectToConsumerBuilderSetUpMap = new EnumMap<>(Subject.class);
        subjectToConsumerBuilderSetUpMap.put(Subject.RUSSIAN_LANG, builder -> builder.russianLang(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.BELORUSSIAN_LANG, builder -> builder.belorussianLang(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.PHYSICS, builder -> builder.physics(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.MATH, builder -> builder.math(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.CHEMISTRY, builder -> builder.chemistry(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.BIOLOGY, builder -> builder.biology(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.FOREIGN_LANG, builder -> builder.foreignLang(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.HISTORY_OF_BELARUS, builder -> builder.historyOfBelarus(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.SOCIAL_STUDIES, builder -> builder.socialStudies(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.GEOGRAPHY, builder -> builder.geography(getRandomSubjectMark()));
        subjectToConsumerBuilderSetUpMap.put(Subject.HISTORY, builder -> builder.history(getRandomSubjectMark()));
    }

    public static Enrollee getRandomEnrollee() {
        return instance.createRandomEnrollee();
    }

    private Enrollee createRandomEnrollee() {
        Enrollee.EnrolleeBuilder builder = Enrollee
            .builder()
            .passportId(getRandomPassportId())
            .countryDomain(getRandomCountryDomain())
            .specialityId(getRandomSpecialityId())
            .surname(getRandomSurname())
            .name(getRandomName())
            .secondName(getRandomSecondName())
            .phoneNumber(getRandomPhoneNumber())
            .certificate(getRandomSubjectMark())
            .date(getCurrentDate());
        setUpEnrolleeMarks(builder);
        return builder.build();
    }

    private static String getRandomPassportId() {
        final int size = 10;
        StringBuilder sb = new StringBuilder(size);
        IntStream.range(0, size).map(i -> getRandomDigit()).forEach(sb::append);
        return sb.toString();
    }

    private static int getRandomDigit() {
        return (int) (Math.random() * 10);
    }

    private static int getRandomIndex(int bound) {
        return (int) (Math.random() * bound);
    }

    private static String getRandomCountryDomain() {
        return CountryDomain.randomDomain().toString();
    }

    private Integer getRandomSpecialityId() {
        return specialityIds.get(getRandomIndex(specialityIds.size()));
    }

    private static String getRandomSurname() {
        return Surname.randomSurname().toString();
    }

    private static String getRandomName() {
        return Name.randomName().toString();
    }

    private static String getRandomSecondName() {
        return SecondName.randomSecondName().toString();
    }

    private static String getRandomPhoneNumber() {
        final int size = 7;
        StringBuilder sb = new StringBuilder("37529");
        IntStream.range(0, size).map(i -> getRandomDigit()).forEach(sb::append);
        return sb.toString();
    }

    private static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    private void setUpEnrolleeMarks(Enrollee.EnrolleeBuilder builder) {
        Set<Subject> usedSubjects = new HashSet<>();
        while (usedSubjects.size() != 3) {
            Subject subject = Subject.randomSubject();
            if (!usedSubjects.contains(subject)) {
                subjectToConsumerBuilderSetUpMap.get(subject).accept(builder);
                usedSubjects.add(subject);
            }
        }
    }

    private static Integer getRandomSubjectMark() {
        return getRandomIndex(101);
    }

    interface RandomValueAccessor {
        static <T extends Enum<T>> T randomValue(Class<T> clazz) {
            T[] values = clazz.getEnumConstants();
            return values[getRandomIndex(values.length)];
        }
    }
    enum CountryDomain {
        BY, RU, US, UA, PL, UK, DE;

        public static CountryDomain randomDomain() {
            return RandomValueAccessor.randomValue(CountryDomain.class);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    enum Surname {
        MAKEDON, KUZMENKO, KIRYANOVA, DUDKOU, ZHUK, IVANOV, PETROV, SIDOROV;

        public static Surname randomSurname() {
            return RandomValueAccessor.randomValue(Surname.class);
        }

        @Override
        public String toString() {
            final String name = name();
            return name.charAt(0) + name.substring(1).toLowerCase();
        }
    }
    enum Name {
        EGOR, ELIZABETH, ANN, ANTON, ALEXANDER, DENIS, IGOR, ARTEM, SERGEY;

        public static Name randomName() {
            return RandomValueAccessor.randomValue(Name.class);
        }

        @Override
        public String toString() {
            final String name = name();
            return name.charAt(0) + name.substring(1).toLowerCase();
        }
    }
    enum SecondName {
        ALEXANDROVICH, ANDREEVICH, DMITRIEVICH, GENADEIEVICH, ALEXANDROVNA, IGOREVICH, IGOREVNA;

        public static SecondName randomSecondName() {
            return RandomValueAccessor.randomValue(SecondName.class);
        }

        @Override
        public String toString() {
            final String name = name();
            return name.charAt(0) + name.substring(1).toLowerCase();
        }
    }
    enum Subject {
        RUSSIAN_LANG, BELORUSSIAN_LANG, PHYSICS, MATH, CHEMISTRY, BIOLOGY, FOREIGN_LANG,
        HISTORY_OF_BELARUS, SOCIAL_STUDIES, GEOGRAPHY, HISTORY;

        public static Subject randomSubject() {
            return RandomValueAccessor.randomValue(Subject.class);
        }
    }
}
