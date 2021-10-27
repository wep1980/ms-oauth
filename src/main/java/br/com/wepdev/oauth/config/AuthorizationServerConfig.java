package br.com.wepdev.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 * Classe que determina que esse micro serviço oauth vai ser um authorization server
 *
 * A Autorização e feita com as credenciais do aplicativo mais as credenciais do usuario, se tudo der certo sera devolvido um token.
 * Para aumentar a segurança o aplicativo que vai chamar o endpoint para gerar o token ele tb tem usuario e senha.
 * Credenciais do aplicativo :
 * cliente_id = myappname123
 * cliente_secret = myappsecret123.
 *
 * Na requisicao esse dados são passados como cabeçalho(Request Header) da requisição, cabeçalho chamado Authorization, o valor dele sera :
 * "Basic " + Base64.encode(cliente_id + ":" + cliente_secret) -> palavra Basic junto com cliente_id e cliente_secret encryptado no algoritmo base64.
 *
 * Credenciais do usuario
 * username - password(senha) - grand_type : password
 *
 * Dados passados no corpo da requisicao com o tipo de parametro Body: x-www-form-unlencoded.
 *
 * Token no formato JWT, esse token utiliza uma assinatura para segurança
 *
 *
 */
@Configuration
@EnableAuthorizationServer
@RefreshScope// Atualiza os valores da variaveis em tempo de execução com actuator, quando uma config e feita no repositorio criado de configuração do github
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {



    @Value("${oauth.client.name}")
    private String clientName;

    @Value("${oauth.client.secret}")
    private String clientSecret;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;


    @Autowired
    private JwtTokenStore tokenStore;


    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }


    /**
     * Metodo que configura a autenticação e autorização com base nas credenciais do aplicativo e do cliente, e tambem o tipo do grant_type
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory() // Autenticao ocorrendo somene em memoria
                .withClient(clientName) // configurando o cliente
                .secret(passwordEncoder.encode(clientSecret)) // configurando a senha do aplicativo ja encodada
                .scopes("read", "write") // Pode ler e escrever
                .authorizedGrantTypes("password") // Tipo do grant_type
                .accessTokenValiditySeconds(86400); // Duracao do token, 24Horas
    }


    /**
     * Metodo que configura o processamento do token
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }
}
