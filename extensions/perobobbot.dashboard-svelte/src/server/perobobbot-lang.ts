
export interface Bot {
  credentials: {[key: string] :any};
  id: string;
  name: string;
  ownerLogin: string;
}

export enum IterationCommand {
CONTINUE,
STOP
}

export enum Platform {
TWITCH,
LOCAL
}

export enum PluginType {
SECONDARY,
EXTENSION,
PRIMARY
}

export enum Role {
THE_BOSS,
ADMINISTRATOR,
TRUSTED_USER,
STANDARD_USER,
ANY_USER
}

