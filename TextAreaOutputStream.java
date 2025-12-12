import java.io.OutputStream;
import java.io.IOException;

public class TextAreaOutputStream extends OutputStream {
    private AdventureGUI gui;

    public TextAreaOutputStream(AdventureGUI gui) {
        this.gui = gui;
    }

    @Override
    public void write(int b) throws IOException {
        gui.appendStory(String.valueOf((char) b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        gui.appendStory(new String(b, off, len));
    }
}
