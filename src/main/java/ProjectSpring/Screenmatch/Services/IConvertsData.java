package ProjectSpring.Screenmatch.Services;

public interface IConvertsData
{
    <T> T getData(String json, Class<T> classe);
}
