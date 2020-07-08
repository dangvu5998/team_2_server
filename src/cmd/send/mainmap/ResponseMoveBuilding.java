package cmd.send.mainmap;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.ResponseConst;
import cmd.receive.mainmap.RequestMoveBuilding;

import java.nio.ByteBuffer;

public class ResponseMoveBuilding extends BaseMsg {
    private final int status;
    private final int buildingId;
    private final int x;
    private final int y;
//    private int buildingErrStatus;
    public ResponseMoveBuilding(int status, int buildingId, int x, int y) {
        super(CmdDefine.MOVE_BUILDING);
        this.status = status;
        this.buildingId = buildingId;
        this.x = x;
        this.y = y;
    }
//    public ResponseMoveBuilding(int status, int buildingId, int x, int y, int buildingErrStatus) {
//        this(status, buildingId, x, y);
//        this.buildingErrStatus = buildingErrStatus;
//    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(status);
        bf.putInt(buildingId);
        bf.putInt(x);
        bf.putInt(y);
//        if(status == ResponseConst.USER_REQUEST_INVALID) {
//            bf.putInt(buildingErrStatus);
//        }
        return packBuffer(bf);
    }
}
