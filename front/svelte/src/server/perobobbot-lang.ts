export interface Bot {
    credentials: { [key: string]: any };
    id: string;
    name: string;
    ownerLogin: string;
}

export enum IterationCommand {
    CONTINUE = 'CONTINUE',
    STOP = 'STOP'
}

export enum Nil {
    NIL = 'NIL'
}

export enum OS {
    LINUX = 'LINUX',
    POSIX_UNIX = 'POSIX_UNIX',
    WINDOWS = 'WINDOWS',
    MACOS = 'MACOS',
    UNKNOWN = 'UNKNOWN'
}

export enum OSInfo {
    INSTANCE = 'INSTANCE'
}

export enum Platform {
    TWITCH = 'TWITCH',
    LOCAL = 'LOCAL'
}

export enum PluginType {
    SECONDARY = 'SECONDARY',
    EXTENSION = 'EXTENSION',
    PRIMARY = 'PRIMARY',
    FRONT_FX = 'FRONT_FX'
}

export enum PointType {
    CREDIT = 'CREDIT'
}

export enum Role {
    THE_BOSS = 'THE_BOSS',
    ADMINISTRATOR = 'ADMINISTRATOR',
    TRUSTED_USER = 'TRUSTED_USER',
    STANDARD_USER = 'STANDARD_USER',
    ANY_USER = 'ANY_USER'
}

export enum TransactionState {
    PENDING = 'PENDING',
    CANCELLED = 'CANCELLED',
    COMPLETED = 'COMPLETED',
    DETACHED = 'DETACHED'
}

