
export interface Credential {
  login: string;
  password: string;
}

export enum Operation {
READ_CREDENTIALS = 'READ_CREDENTIALS'
}

export interface Role {
  operations: Operation[];
  roleKind: RoleKind;
}

export enum RoleKind {
ADMIN = 'ADMIN',
USER = 'USER'
}

export interface SimpleUser {
  locale: any;
  login: string;
  roles: RoleKind[];
}

