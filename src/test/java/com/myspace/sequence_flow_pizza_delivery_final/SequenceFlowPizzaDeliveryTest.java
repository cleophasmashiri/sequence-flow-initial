package com.myspace.sequence_flow_pizza_delivery_final;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import com.myspace.model.Delivery;
import com.myspace.model.Order;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.EmptyContext;

public class SequenceFlowPizzaDeliveryTest extends JbpmJUnitBaseTestCase {

    private String processId = "sequenceFlowPizzaDelivery";
    private RuntimeEngine engine;
    private KieSession kieSession;

    public SequenceFlowPizzaDeliveryTest() {
        super();
    }

    @Before
    public void setup() { 
        createRuntimeManager("com/myspace/sequence_flow_pizza_delivery_final/sequenceFlowPizzaDelivery.bpmn");
        engine = getRuntimeEngine(EmptyContext.get());
        kieSession = engine.getKieSession();
    }

    @Test
    public void givenCollectPizzaShouldFollowCollect() {
        assertNotNull(kieSession);
        Map<String, Object> params = new HashMap<String, Object>();
        Order order = createOrder("collect");
        params.put("order", order);
        ProcessInstance pi = kieSession.startProcess(processId, params);
        assertNotNull(pi);
        this.assertNodeTriggered(pi.getId(), "Start", "Process Order", "End");
    }

    private Order createOrder(String deliveryMethod) {
        Order order = new Order();      
        order.setDelivery(new Delivery());
        order.getDelivery().setDelivered(true);
        if (deliveryMethod!=null)
            order.getDelivery().setDeliveryMethod(deliveryMethod);
        return order;
    }
    
}
