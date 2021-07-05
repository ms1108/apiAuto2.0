package linkcall;

import api.ApiTest;

import java.util.List;

public class LinkCallRun extends ApiTest {

    public void linkCallRun(List<LinkCallEntry> linkCallEntries){
        for (LinkCallEntry linkCallEntry : linkCallEntries) {
            linkCallRun(linkCallEntry);
        }
    }

    public void linkCallRun(LinkCallEntry linkCallEntry){

    }
}
