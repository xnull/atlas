package bynull.poi.rest.messages;

/**
 * Successful operation
 * Created by null on 3/26/16.
 */
public class OkMessage implements Message {
    private String result;

    private OkMessage(){

    }

    public OkMessage(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
