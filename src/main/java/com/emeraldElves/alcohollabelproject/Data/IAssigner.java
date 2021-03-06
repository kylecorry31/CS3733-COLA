package com.emeraldElves.alcohollabelproject.Data;

import java.util.List;

/**
 * Created by Joe on 4/9/2017.
 */
public interface IAssigner {
    /**
     * assignAgent(): somehow assigns agent to application
     * @param agentNames
     * @param nextAgentToAssign
     * @return agent name
     */
    String assignAgent(List<String> agentNames, String nextAgentToAssign);
}