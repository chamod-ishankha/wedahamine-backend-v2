package org.bytecub.WedahamineBackend.dto.miscellaneous;


import java.io.Serializable;

/**
 * The type Pagination dto.
 *
 * @author Sujith T <p> <!In God We Trust>
 */
public class PaginationDto implements Serializable {

    private static final long serialVersionUID = 6560239736776971781L;

    private int from;
    private int to;
    private int count;
    private int total;

    /**
     * Gets from.
     *
     * @return the from
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
