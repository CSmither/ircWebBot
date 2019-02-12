package org.smither.ircWebBot;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Actor;
import org.kitteh.irc.client.library.util.AcceptingTrustManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IrcController {
    private Client client;

    @Autowired
    public IrcController(@Value("${irc.nick}") String nick, @Value("${irc.name}") String name,
                         @Value("${irc.host}") String host, @Value("${irc.port}") Integer port, @Value("${irc" +
            ".sslEnabled}") Boolean sslEnabled, @Value("${irc.password}") String password) {
        this.client =
                Client.builder().nick(nick).name(name).realName(name).server().host(host).port(port).secure(sslEnabled).secureTrustManagerFactory(new AcceptingTrustManagerFactory()).password(password).then().buildAndConnect();
        this.client.getEventManager().registerEventListener(this);
    }

    public Optional<Throwable> sendMessage(String channel, String message) {
        try {
            if (!client.getChannels().contains(channel)) {
                client.addChannel(channel);
            }
            client.sendMessage(channel, message);
            return Optional.empty();
        } catch (Throwable e) {
            return Optional.of(e);
        }
    }

    public Optional<Throwable> connectChannel(String channel, Optional<String> password) {
        try {
            if (client.getChannels().contains(channel)) {
                return Optional.empty();
            }
            if (password.isPresent()) {
                client.addKeyProtectedChannel(channel, password.get());
                return Optional.empty();
            } else {
                client.addChannel(channel);
                return Optional.empty();
            }
        } catch (Throwable e) {
            return Optional.of(e);
        }
    }

    public Collection<String> getChannels() {
        return client.getChannels().parallelStream().map(Actor::getName).collect(Collectors.toSet());
    }
}
