package br.com.pedrojaber.infra.config;

import br.com.pedrojaber.messaging.UrgentFeedbackEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = { UrgentFeedbackEvent.class })
public class ReflectionConfig {

}
