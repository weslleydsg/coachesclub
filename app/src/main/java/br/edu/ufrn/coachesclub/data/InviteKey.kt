package br.edu.ufrn.coachesclub.data

import br.edu.ufrn.coachesclub.utils.Utils

data class InviteKey(val userEmail: String? = null, var invalid: Boolean = false, val id: String = Utils.getRandomString(8))
