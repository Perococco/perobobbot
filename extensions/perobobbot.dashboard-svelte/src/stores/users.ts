import type {RoleKind, SimpleUser} from "@backend/security-com";
import {UserController} from "@backend/rest-controller";
import type {Readable, Writable} from "svelte/types/runtime/store";
import {writable} from "svelte/store";


declare interface InnerUserStore extends UsersStore, Writable<User[]> {
    set: (users: User[]) => void;
    clear: () => void;
}

declare interface UsersStore extends Readable<User[]> {
    initialize: () => Promise<void>
    refreshAll: () => Promise<void>
}

class User implements SimpleUser {
    private usersStore: InnerUserStore;
    deactivated: boolean;
    locale: string;
    login: string;
    roles: RoleKind[];


    constructor(usersStore: InnerUserStore, simpleUser: SimpleUser) {
        this.usersStore = usersStore;
        this.deactivated = simpleUser.deactivated;
        this.locale = simpleUser.locale;
        this.login = simpleUser.login;
        this.roles = simpleUser.roles;
    }


}


function createUserStore(): InnerUserStore {
    const _userStore = writable<User[]>([]);
    const controller = new UserController();

    let initialized: boolean = false;

    return {
        subscribe: _userStore.subscribe,
        update: _userStore.update,
        set: _userStore.set,

        initialize: async function (): Promise<void> {
            if (initialized) {
                return Promise.resolve();
            }
            await this.refreshAll();
            initialized = true;
        },

        clear() {
            this.set([])
        },

        refreshAll: async function (): Promise<void> {
            return controller.listAllUsers()
                .then(u => u.map(i => new User(this, i)))
                .then(u => this.set(u))
                .catch(err => {
                    console.error(err);
                    this.set([]);
                    throw err;
                })
        }
    }

}

const users: UsersStore = createUserStore();

export {users,User};

