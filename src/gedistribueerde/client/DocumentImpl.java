package gedistribueerde.client;

import java.awt.event.TextListener;

public class DocumentImpl implements Document {
    private StringBuilder text;
    private TextListener textListener;

    public DocumentImpl() {
        this.text = new StringBuilder();
        this.textListener = null;
    }

    public DocumentImpl(String text) {
        this.text = new StringBuilder(text);
        this.textListener = null;
    }

    public void setTextListener(TextListener textListener) {
        this.textListener = textListener;
    }

    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void setText(String text) {
        this.text = new StringBuilder(text);
        if (textListener != null) {
            textListener.textValueChanged(null);
        }
    }

    @Override
    public void append(char c) {
        this.text.append(c);
        if (textListener != null) {
            textListener.textValueChanged(null);
        }
    }

    @Override
    public void setChar(int position, char c) {
        this.text.setCharAt(position, c);
        if (textListener != null) {
            textListener.textValueChanged(null);
        }
    }
}
