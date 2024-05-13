package by.makedon.selectioncommittee.app.entity.enrolleeform;

import lombok.ToString;

/**
 * @author Yahor Makedon
 */
@ToString
public final class NullEnrolleeForm extends EnrolleeForm {
    private static final NullEnrolleeForm instance = new NullEnrolleeForm();

    private NullEnrolleeForm() {
        super(null, null, null);
    }

    public static NullEnrolleeForm getInstance() {
        return instance;
    }

    @Override
    public String getUniversity() {
        return "";
    }

    @Override
    public String getFaculty() {
        return "";
    }

    @Override
    public String getSpeciality() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getSurname() {
        return "";
    }

    @Override
    public String getSecondName() {
        return "";
    }

    @Override
    public String getPassportId() {
        return "";
    }

    @Override
    public String getCountryDomen() {
        return "";
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public String getDate() {
        return "";
    }

    @Override
    public byte getRussianLang() {
        return 0;
    }

    @Override
    public byte getBelorussianLang() {
        return 0;
    }

    @Override
    public byte getPhysics() {
        return 0;
    }

    @Override
    public byte getMath() {
        return 0;
    }

    @Override
    public byte getChemistry() {
        return 0;
    }

    @Override
    public byte getBiology() {
        return 0;
    }

    @Override
    public byte getForeignLang() {
        return 0;
    }

    @Override
    public byte getHistoryOfBelarus() {
        return 0;
    }

    @Override
    public byte getSocialStudies() {
        return 0;
    }

    @Override
    public byte getGeography() {
        return 0;
    }

    @Override
    public byte getHistory() {
        return 0;
    }

    @Override
    public byte getCertificate() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
