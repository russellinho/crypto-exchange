package com.russ.cryptoexchange;

import java.io.Serializable;
import java.util.Properties;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class WalletGenerator implements IdentifierGenerator, Configurable {
    private static final String ADDRESS_PREFIX = "0x";
    private static final short ADDRESS_LENGTH = 16;

    @Override
    public Serializable generate(
      SharedSessionContractImplementor session, Object obj) 
      throws HibernateException {

        String query = String.format("select %s from %s", 
            session.getEntityPersister(obj.getClass().getName(), obj)
              .getIdentifierPropertyName(),
            obj.getClass().getSimpleName());

        Object[] ids = session.createQuery(query).stream().toArray();
        // ids.arrString[] stringArray = stringStream.toArray(String[]::new);

        String newAddress = ADDRESS_PREFIX + generateRandomAddress();
        boolean valid = false;
        // Predicate<String> p1 = s -> s.equals(newAddress);

        while (!valid) {
          valid = true;
          for (Object o : ids) {
            String tId = (String)o;
            if (tId.equals(newAddress)) {
              newAddress = ADDRESS_PREFIX + generateRandomAddress();
              valid = false;
              break;
            }
          }
        }

        return newAddress;
    }

    @Override
    public void configure(Type type, Properties properties, 
      ServiceRegistry serviceRegistry) throws MappingException {
        // prefix = properties.getProperty("prefix");
    }

    protected String generateRandomAddress() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < ADDRESS_LENGTH) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
