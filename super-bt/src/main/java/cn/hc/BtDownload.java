package cn.hc;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;



import java.io.File;
import java.nio.file.Path;

/**
 * Created by super on 2017/11/16
 */
public class BtDownload {
    public static void main(String[] args) {
        String url = "magnet:?xt=urn:btih:eb1688dc287681bf373a77b83856f6b785f5b7f1";
        Storage storage = new FileSystemStorage(new File("win10.iso").toPath());
        DHTModule dhtModule = new DHTModule(new DHTConfig() {
            public boolean setShouldUseRouterBootstrap() {
                return true;
            }
        });
        BtClient client = Bt.client().magnet(url).storage(storage).autoLoadModules().module(dhtModule).build();
        client.startAsync(state ->{
            if (state.getPiecesRemaining() == 0){
                client.stop();
            }
        },1000).join();
    }

}
