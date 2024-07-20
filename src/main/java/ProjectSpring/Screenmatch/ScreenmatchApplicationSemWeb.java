//package ProjectSpring.Screenmatch;
//
//
//import ProjectSpring.Screenmatch.Principal.Principal;
//import ProjectSpring.Screenmatch.Repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationSemWeb implements CommandLineRunner
//{
//	@Autowired
//	private SerieRepository repository;
//
//	public static void main(String[] args)
//	{
//
//		SpringApplication.run(ScreenmatchApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception
//	{
//		Principal principal = new Principal(repository);
//		principal.ViewMenu();
//
//	}
//}
