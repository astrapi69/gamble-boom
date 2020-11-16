package io.github.astrapi69.gambleboom.jpa.listener;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import de.alpharogroup.sign.JsonSigner;
import de.alpharogroup.spring.autowire.AutowireAware;
import io.github.astrapi69.gambleboom.jpa.entity.Draws;

@Log
public class DrawsSignatureListener
{
	@Autowired
	JsonSigner<Draws> drawsJsonSigner;

	@PrePersist
	public void onPrePersist(Draws verifiable)
	{
		sign(verifiable);
	}

	@PreUpdate
	public void onPreUpdate(Draws verifiable)
	{
		sign(verifiable);
	}

	@PostLoad
	public void onPostLoad(Draws verifiable) {
		log.info("Draws with id:" + verifiable.getId() + " have been loaded");
	}

	private void sign(Draws verifiable)
	{
		AutowireAware.autowire(this, drawsJsonSigner);
		String digitalSignature = drawsJsonSigner.sign(verifiable);
		verifiable.setSignature(digitalSignature);
	}

}
