package com.springboot.sample.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by VDE on 10 Sept 2018.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
		        .inMemory()
		        .withClient(clientId)
		        .secret(clientSecret)
		        .authorizedGrantTypes("client_credentials", "password", "refresh_token", "authorization_code")
		        .scopes(scopeRead, scopeWrite)
		        .accessTokenValiditySeconds(1296000) //15 days = 1296000 seconds
		        .refreshTokenValiditySeconds(2592000)
		        .resourceIds(resourceIds);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore)
		        .accessTokenConverter(accessTokenConverter)
		        .tokenEnhancer(enhancerChain).userDetailsService(userDetailsService)
		        .authenticationManager(authenticationManager);
	}

}
