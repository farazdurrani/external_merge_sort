
public class Tracker {

    private String line;
    private boolean skip;

    public Tracker(String currLine, boolean skip) {
	this.line = currLine;
	this.skip = skip;
    }

    public String getLine() {
	return line;
    }

    public void setLine(String line) {
	this.line = line;
    }

    public boolean isSkip() {
	return skip;
    }

    public void setSkip(boolean skip) {
	this.skip = skip;
    }

}
