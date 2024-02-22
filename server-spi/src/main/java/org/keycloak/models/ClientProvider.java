/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.models;

import org.keycloak.provider.Provider;
import org.keycloak.storage.client.ClientLookupProvider;

import java.util.List;

/**
 * Provider of the client records.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface ClientProvider extends ClientLookupProvider, Provider {

    /**
     * Returns the clients of the given realm.
     * @param realm Realm.
     * @param firstResult First result to return. Ignored if negative or {@code null}.
     * @param maxResults Maximim number of results to return. Ignored if negative or {@code null}.
     * @return List of the clients. Never returns {@code null}.
     */
    List<ClientModel> getClients(RealmModel realm, Integer firstResult, Integer maxResults);

    /**
     * Returns all the clients of the given realm.
     * Effectively the same as the call {@code getClients(realm, null, null)}.
     * @param realm Realm.
     * @return List of the clients. Never returns {@code null}.
     */
    default List<ClientModel> getClients(RealmModel realm) {
        return this.getClients(realm, null, null);
    }

    /**
     * Adds a client with given {@code clientId} to the given realm.
     * The internal ID of the client will be created automatically.
     * @param realm Realm owning this client.
     * @param clientId String that identifies the client to the external parties.
     *   Maps to {@code client_id} in OIDC or {@code entityID} in SAML.
     * @return Model of the created client.
     */
    default ClientModel addClient(RealmModel realm, String clientId) {
        return addClient(realm, null, clientId);
    }

    /**
     * Adds a client with given internal ID and {@code clientId} to the given realm.
     * @param realm Realm owning this client.
     * @param id Internal ID of the client or {@code null} if one is to be created by the underlying store
     * @param clientId String that identifies the client to the external parties.
     *   Maps to {@code client_id} in OIDC or {@code entityID} in SAML.
     * @return Model of the created client.
     * @throws IllegalArgumentException If {@code id} does not conform
     *   the format understood by the underlying store.
     */
    ClientModel addClient(RealmModel realm, String id, String clientId);

    /**
     * Returns number of clients in the given realm
     * @param realm Realm.
     * @return Number of the clients in the given realm.
     */
    long getClientsCount(RealmModel realm);

    /**
     * Returns a list of clients that are expected to always show up in account console.
     * @param realm Realm owning the clients.
     * @return List of the clients. Never returns {@code null}.
     */
    List<ClientModel> getAlwaysDisplayInConsoleClients(RealmModel realm);

    /**
     * Removes given client from the given realm.
     * @param id Internal ID of the client
     * @param realm Realm.
     * @return {@code true} if the client existed and has been removed, {@code false} otherwise.
     * @deprecated Use {@link #removeClient(RealmModel, String)} instead.
     */
    default boolean removeClient(String id, RealmModel realm) { return this.removeClient(realm, id); }

    /**
     * Removes given client from the given realm.
     * @param realm Realm.
     * @param id Internal ID of the client
     * @return {@code true} if the client existed and has been removed, {@code false} otherwise.
     */
    boolean removeClient(RealmModel realm, String id);

    /**
     * Removes all clients from the given realm.
     * @param realm Realm.
     */
    void removeClients(RealmModel realm);
}
