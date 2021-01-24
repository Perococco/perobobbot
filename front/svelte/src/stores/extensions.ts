import {ExtensionController} from "@backend/rest-controller";
import type {Readable, Writable} from "svelte/types/runtime/store";
import {writable} from "svelte/store";
import type {Extension} from "@backend/data-com";


declare interface InnerExtensionsStore extends ExtensionsStore, Writable<FrontExtension[]> {
    set: (users: FrontExtension[]) => void;
    clear: () => void;
}

declare interface ExtensionsStore extends Readable<FrontExtension[]> {
    initialize: () => Promise<void>
    refreshAll: () => Promise<void>
}

class FrontExtension implements Extension {
    private usersStore: InnerExtensionsStore;
    activated: boolean;
    name: string;

    constructor(usersStore: InnerExtensionsStore, simpleUser: Extension) {
        this.usersStore = usersStore;
        this.activated = simpleUser.activated;
        this.name = simpleUser.name;
    }


}


function createExtensionStore(): InnerExtensionsStore {
    const _userStore = writable<FrontExtension[]>([]);
    const controller = new ExtensionController();

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
            return controller.listExtensions()
                .then(u => u.map(i => new FrontExtension(this, i)))
                .then(u => this.set(u))
                .catch(err => {
                    console.error(err);
                    this.set([]);
                    throw err;
                })
        }
    }

}

const extensions: ExtensionsStore = createExtensionStore();

export {extensions,FrontExtension};

