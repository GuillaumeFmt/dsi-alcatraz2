package core.usecase;

import ports.in.ClientAcknowledge;

public class AcknowledgeUseCase implements ClientAcknowledge {

    @Override
    public boolean sendAcknowledge() {
        return true;
    }
}
