import {Authentication, noAuthentication} from "../types/authentication";
import {sessionWritable} from "./session-store";


export let authentication = sessionWritable<Authentication>("authentication", noAuthentication());
