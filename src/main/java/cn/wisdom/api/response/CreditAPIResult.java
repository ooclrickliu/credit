package cn.wisdom.api.response;

import cn.wisdom.common.model.JsonDocument;

public class CreditAPIResult extends JsonDocument
{
    public static final JsonDocument SUCCESS = new CreditAPIResult();

    private static final String SERVICE_DOORBELL_PAYMENT = "Credit";

    public CreditAPIResult()
    {
        super(SERVICE_DOORBELL_PAYMENT, JsonDocument.STATE_SUCCESS);
    }

    public CreditAPIResult(Object data)
    {
        super(SERVICE_DOORBELL_PAYMENT, data);
    }

    public CreditAPIResult(String errCode)
    {
        super(SERVICE_DOORBELL_PAYMENT, errCode);
    }

}
