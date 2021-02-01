package perobobbot.lang;

public interface Transaction {

    long amount();

    void complete();

    void rollback();

}
