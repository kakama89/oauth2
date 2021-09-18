package com.oas.security.oauth.token.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
public abstract class AbstractTokenStoreConfigurer{
    protected final DataSource dataSource;
    
    @Autowired
    public AbstractTokenStoreConfigurer(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
	public ApprovalStore approvalStore(){
		return new JdbcApprovalStore(dataSource);
    }

    public abstract TokenEnhancerChain tokenEnhancerChain();
    public abstract TokenStore tokenStore();
}