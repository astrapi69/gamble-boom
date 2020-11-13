package io.github.astrapi69.gambleboom.jpa.listeners;

import de.alpharogroup.sign.JsonSigner;
import de.alpharogroup.spring.autowire.AutowireAware;
import io.github.astrapi69.gambleboom.jpa.entities.Draws;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class DrawsSignatureListener
{
	@Autowired JsonSigner<Draws> drawsJsonSigner;

	@PrePersist
	@PreUpdate
	public void onPrePersistSign(Draws verifiable)
	{
		sign(verifiable);
	}

	private void sign(Draws verifiable)
	{
		AutowireAware.autowire(this, drawsJsonSigner);
		String digitalSignature = drawsJsonSigner.sign(verifiable);
		verifiable.setSignature(digitalSignature);
	}

}
