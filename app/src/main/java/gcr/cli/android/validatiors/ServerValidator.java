package gcr.cli.android.validatiors;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import gcr.cli.android.R;
import gcr.cli.android.models.IServer;
import gcr.cli.android.validatiors.errorkeys.ServerErrorKeys;

public class ServerValidator extends ModelValidator<IServer, ServerErrorKeys> {

    public ServerValidator(Context context) {
        this.context = context;
    }

    public ServerErrorKeys validateId(int id) {
        final String regex = "^[1-9][0-9]*$";
        final String idStr = id + "";
        return validateData(regex, ServerErrorKeys.ID, idStr);
    }

    public ServerErrorKeys validateName(String name) {
        final String regex = ".+";
        return validateData(regex, ServerErrorKeys.NAME, name);
    }

    public ServerErrorKeys validateAddress(String address) {
        // Hostname specification: [RFC 1123] http://tools.ietf.org/html/rfc1123
        final String hostnameRegex = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
        final String ipAddressRegex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
        if(validateData(hostnameRegex, ServerErrorKeys.ADDRESS, address) != null) {
            return validateData(ipAddressRegex, ServerErrorKeys.ADDRESS, address);
        }
        return null;
    }

    public ServerErrorKeys validateSocketPort(int socketPort) {
        final String regex = "^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[0-9]{1,4})$|^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[1-5][0-9]{4}|[0-9]{1,4})$";
        final String socketPortStr = socketPort + "";
        return validateData(regex, ServerErrorKeys.SOCKET_PORT, socketPortStr);
    }

    @Override
    public List<ServerErrorKeys> validate(IServer model) {
        List<ServerErrorKeys> errorKeys = new ArrayList<>();

        ServerErrorKeys errorKey = validateId(model.getId());
        if(errorKey != null) {
            errorKeys.add(errorKey);
        }
        errorKey = validateName(model.getName());
        if(errorKey != null) {
            errorKeys.add(errorKey);
        }
        errorKey = validateAddress(model.getAddress());
        if(errorKey != null) {
            errorKeys.add(errorKey);
        }
        errorKey = validateSocketPort(model.getSocketPort());
        if(errorKey != null) {
            errorKeys.add(errorKey);
        }
        return errorKeys;
    }

    @Override
    public String getErrorMessage(ServerErrorKeys key) {
        switch(key) {
            case ID:
                return context.getResources().getString(R.string.server_error_key_id);
            case NAME:
                return context.getResources().getString(R.string.server_error_key_name);
            case ADDRESS:
                return context.getResources().getString(R.string.server_error_key_address);
            case SOCKET_PORT:
                return context.getResources().getString(R.string.server_error_key_socket_port);
        }
        return null;
    }
}
