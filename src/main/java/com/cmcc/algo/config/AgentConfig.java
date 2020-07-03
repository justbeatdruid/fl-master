package com.cmcc.algo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AgentConfig {
    @Value("${fl-agent.ip}")
    private String ip;

    @Value("${fl-agent.service}")
    private String service;

    @Value("${fl-agent.port}")
    private String port;

    public String getAgentUrl(Integer partyId){
        return "http://" + service + ".fate-" + partyId.toString() + ":" + port;
    }

    public String getAgentUrl(String partyId){
        return "http://" + service + ".fate-" + partyId + ":" + port;
    }
}
