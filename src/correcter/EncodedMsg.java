package correcter;

class EncodedMsg {
    final String expand;
    final String parity;

    EncodedMsg(String expand, String parity) {
        this.expand = expand;
        this.parity = parity;
    }

    String getExpand() {
        return expand;
    }

    String getParity() {
        return parity;
    }

    @Override
    public String toString() {
        return "expand: " + getExpand() + "\n" + "parity: " + getParity();
    }
}
