package sc.com.assessment.cashman.entity;

import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "denomination")
@Entity
public class DenominationEntity implements Cloneable {

    @Id
    @Column(name = "denomination_type")
    private String denominationType;

    @Column(name = "denomination_value")
    private BigInteger denominationValue;

    @Column(name = "denomination_count")
    private int denominationCount;

    public String getDenominationType() {
        return denominationType;
    }

    public void setDenominationType(final String denominationType) {
        this.denominationType = denominationType;
    }

    public BigInteger getDenominationValue() {
        return denominationValue;
    }

    public void setDenominationValue(final BigInteger denominationValue) {
        this.denominationValue = denominationValue;
    }

    public int getDenominationCount() {
        return denominationCount;
    }

    public void setDenominationCount(final int denominationCount) {
        this.denominationCount = denominationCount;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final DenominationEntity that = (DenominationEntity) object;
        return denominationCount == that.denominationCount
                && denominationType.equals(that.denominationType)
                && denominationValue.equals(that.denominationValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denominationType, denominationValue, denominationCount);
    }

    @Override
    public String toString() {
        return "DenominationEntity{"
                + "denominationType='" + denominationType + '\''
                + ", denominationValue=" + denominationValue
                + ", denominationCount=" + denominationCount
                + '}';
    }

    @Override
    public DenominationEntity clone() throws CloneNotSupportedException {
        return (DenominationEntity) super.clone();
    }
}
