import java.io.Serializable;

public class PermutationRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int n;
    private int r;

    public PermutationRequest(int n, int r) {
        this.n = n;
        this.r = r;
    }

    public int getN() {
        return n;
    }

    public int getR() {
        return r;
    }
}
