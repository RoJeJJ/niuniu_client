package com.ruidi.niuniu.listener;

import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;


public abstract class OnConnectionListener implements IEventListener {
    @Override
    public void dispatch(BaseEvent baseEvent) throws SFSException {
        onConnection(baseEvent);
    }
    public abstract void onConnection(BaseEvent baseEvent);
}
