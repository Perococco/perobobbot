
export interface Credential {
  login: string;
  password: string;
}

export enum Operation {
}

export interface Role {
  operations: Operation[];
  roleKind: RoleKind;
}

export enum RoleKind {
ADMIN,
USER
}

export interface SimpleUser {
  login: string;
  roles: RoleKind[];
}

