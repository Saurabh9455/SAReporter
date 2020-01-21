package com.SuperemeAppealReporter.api.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
	      /*
        for using spring security, you can get the currently logged username with following code segment.  */
		String user =null;
		if(SecurityContextHolder.getContext().getAuthentication()!=null)
		{
		 user =     SecurityContextHolder.getContext().getAuthentication().getName();
		}
  if(user!=null)
  {
  return Optional.ofNullable(user);
  }
  else
  {
	  return Optional.ofNullable("system");
  }
	}

}
