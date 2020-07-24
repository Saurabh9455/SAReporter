package com.SuperemeAppealReporter.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.service.UserService;

@SuppressWarnings("deprecation")

@EnableWebSecurity /** This is a marker annotation. When used with @Configuration this annotation
* switch off the default web security and lets us to write our own by
* implementing WebSecurityConfigurerAdapter
**/
//@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	

		@Autowired
		private  UserService userService;

		@Autowired
		private JWTAuthenticationEntryPoint aunthenticationHandler;
		
	
	    @Autowired private JWTAcessDeniedHandler accessDeniedHandler;
	 
		
	    
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST,RestMappingConstant.User.FULL_SIGN_UP_URI).permitAll()
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getRoleMasterData").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/admin/getSingleCase/{docId}").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/getClientList").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/admin/getCourtBranchByCourtId/{courtId}").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addClient").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getCountryMasterData").permitAll()
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getCityMasterData/{stateId}").permitAll()
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getStateMasterData/{countryId}").permitAll()
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addClient").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/getStaffList").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addStaff").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteStaff").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/updateStaff").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteClient").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/updateClient").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addPlan").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deletePlan").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/master/getNextDocId").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/searchClient").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addCase").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/uploadCasePf").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/searchStaff").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/getCaseList").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			/*.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/admin/getCasePdf").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")*/
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addCourt").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteCourt").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/addCourtBranch").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteCourtBranch").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/getCourtList").hasAnyRole("ADMIN",
					"SUPER_ADMIN")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/admin/getDashBoard").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/admin/getDashBoard").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/editCase").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteCase").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/admin/deleteCase").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/payment/initiatePayment").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/payment/confirmPayment").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/dashboardSearch").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/user/loginHistory").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/user/loginHistoryByClientIdForAdminPanel").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/user/logout").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/addCaseToMyLibrary").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			
			
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/user/getMyLibraryCaseList").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/user/deleteCaseFromMyLibrary/{docId}").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/resetPassword").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/uploadProfilePicture").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			.antMatchers(HttpMethod.POST,"/SuperemeAppealReporter/v1/api/user/uploadProfilePicture").hasAnyRole("ADMIN",
					"SUPER_ADMIN","DATA_ENTRY_OPERATOR","USER")
			
			
			
		
			.anyRequest().authenticated()
			.and()
			.addFilter(getAuthenticaionFilter())
			.exceptionHandling()
		    .authenticationEntryPoint(aunthenticationHandler)
		    .and()
		    .addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
		    .sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		
		}

		/*
		 * @Override public void configure(WebSecurity web) throws Exception {
		 * web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
		 * "/swagger-resources/**", "/configuration/**", "/swagger-ui.html",
		 * "/webjars/**"); }
		 */
		
		 @Override
		    public void configure(WebSecurity webSecurity) throws Exception
	{
		webSecurity.ignoring().antMatchers("/SuperemeAppealReporter/v1/api/user/signup",
				"/SuperemeAppealReporter/v1/api/user/forgetPassword",
				"/SuperemeAppealReporter/v1/api/user/verifyEmail",
				"/SuperemeAppealReporter/v1/api/user/validateResetPasswordLink",
				"/SuperemeAppealReporter/v1/api/admin/getCasePdf",
				"/SuperemeAppealReporter/v1/api/user/resetPassword",
				"/SuperemeAppealReporter/v1/api/admin/getPlanList",
				"/SuperemeAppealReporter/v1/api/master/getCountryMasterData",
				"/SuperemeAppealReporter/v1/api/master/getCityMasterData/{stateId}",
				"/SuperemeAppealReporter/v1/api/master/getStateMasterData/{countryId}",
				"/SuperemeAppealReporter/v1/api/admin/getSingleCaseForGuest/{docId}",
		/*
		 * .antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getCountryMasterData").permitAll()
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getCityMasterData/{stateId}").permitAll()
			.antMatchers(HttpMethod.GET,"/SuperemeAppealReporter/v1/api/master/getStateMasterData/{countryId}").permitAll()
				
		 */
				
				
      /*          "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",

                "",
                "",
                "",
                "",

                "",
                "",*/
                "/SuperemeAppealReporter/v1/api/admin/getCourtListV2",
              
               /* "",
                "",
                "",*/
				"/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**"); // skip security entirely
	}
		
		  @Override protected void configure(AuthenticationManagerBuilder auth) throws
		  Exception {
		  auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); 
		  }
		 

		/*
		 * @Bean public AuthenticationProvider authProvider() {
		 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		 * provider.setUserDetailsService(userService); provider.setPasswordEncoder(
		 * (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance()); return provider;
		 * 
		 * }
		 */
		
		
	 /* (name = "bCryptPasswordEncoder") */
		public PasswordEncoder passwordEncoder()
		{
			return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
			//return new BCryptPasswordEncoder();
		}
		
		
		public JWTAuthenticationFilter getAuthenticaionFilter() throws Exception
		{
			final JWTAuthenticationFilter authenticationFilter  = new JWTAuthenticationFilter(authenticationManager());
		   authenticationFilter.setFilterProcessesUrl("/SuperemeAppealReporter/v1/api/user/signin");
		  //  System.out.println("MY ------------>>"+RestMappingConstant.User.USER_SIGN_IN_DEV_URL);
		    return authenticationFilter;
		}
	}

	

