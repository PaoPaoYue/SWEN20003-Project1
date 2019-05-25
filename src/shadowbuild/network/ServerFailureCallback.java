package shadowbuild.network;

@FunctionalInterface
public interface ServerFailureCallback {
    public void run(int id);
}
