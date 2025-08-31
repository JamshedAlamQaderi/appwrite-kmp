package com.jamshedalamqaderi.kmp.appwrite

/**
 * Helper class to generate Appwrite permission strings.
 *
 * Use together with [Role] to compose permission expressions to be supplied
 * to SDK methods that accept a list of permissions (for example when creating
 * or updating documents, files, rows, etc.).
 *
 * Examples:
 * - Permission.read(Role.any()) -> read("any")
 * - Permission.write(Role.user("USER_ID")) -> write("user:USER_ID")
 * - Permission.create(Role.team("TEAM_ID", role = "owner")) -> create("team:TEAM_ID/owner")
 *
 * See Appwrite permissions documentation for details.
 */
class Permission {
    companion object {
        /** Returns a read permission for the given [role]. */
        fun read(role: String): String {
            return "read(\"${role}\")"
        }

        /** Returns a write permission for the given [role]. */
        fun write(role: String): String {
            return "write(\"${role}\")"
        }

        /** Returns a creation permission for the given [role]. */
        fun create(role: String): String {
            return "create(\"${role}\")"
        }

        /** Returns an update permission for the given [role]. */
        fun update(role: String): String {
            return "update(\"${role}\")"
        }

        /** Returns a delete permission for the given [role]. */
        fun delete(role: String): String {
            return "delete(\"${role}\")"
        }
    }
}
