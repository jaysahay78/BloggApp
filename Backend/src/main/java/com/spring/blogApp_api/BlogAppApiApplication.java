package com.spring.blogApp_api;

import com.spring.blogApp_api.config.AppConstants;
import com.spring.blogApp_api.entities.Role;
import com.spring.blogApp_api.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepo roleRepo;

    public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			Role role = new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("ROLE_USER");

			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");

			List<Role> roles = List.of(role, role1);

			List<Role> result = roleRepo.saveAll(roles);

			result.forEach(r->{
				System.out.println(r.getName());
			});
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
}
