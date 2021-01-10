import {Platform} from './perobobbot-lang';

export interface CreateBotParameters {
  name: string;
}

export interface RestCredentialInfo {
  id: string;
  login: string;
  nick: string;
  platform: Platform;
  secretAvailable: boolean;
}

