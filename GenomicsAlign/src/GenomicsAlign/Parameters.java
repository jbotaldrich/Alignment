/*
 * Class for storing the current parameter set for the alignment
 */
package GenomicsAlign;

/**
 *
 * @author Joshua
 */
public class Parameters {
    int match = 1;
    int mismatch = -2;
    int g = -1;
    int h = -5;

    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }

    public int getMismatch() {
        return mismatch;
    }

    public void setMismatch(int mismatch) {
        this.mismatch = mismatch;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
