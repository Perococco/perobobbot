import {Authentication, noAuthentication} from "../types/authentication";
import {sessionWritable} from "./session-storage-store";


export let authentication = sessionWritable<Authentication>("authentication", noAuthentication());
