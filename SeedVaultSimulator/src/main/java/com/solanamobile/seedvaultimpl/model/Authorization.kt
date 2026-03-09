/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solanamobile.seedvaultimpl.model

import android.os.Process
import com.solanamobile.seedvault.WalletContractV1

data class Authorization(
    val uid: Int,
    @WalletContractV1.AuthToken val authToken: Long,
    val purpose: Purpose
) {
    enum class Purpose {
        SIGN_SOLANA_TRANSACTIONS,
        SIGN_ALGORAND_TRANSACTIONS,
        SIGN_BITCOIN_TRANSACTIONS,
        SIGN_ETHEREUM_TRANSACTIONS;

        fun toWalletContractConstant(): Int {
            return when (this) {
                SIGN_SOLANA_TRANSACTIONS -> WalletContractV1.PURPOSE_SIGN_SOLANA_TRANSACTION
                SIGN_ALGORAND_TRANSACTIONS -> WalletContractV1.PURPOSE_SIGN_ALGORAND_TRANSACTION
                SIGN_BITCOIN_TRANSACTIONS -> WalletContractV1.PURPOSE_SIGN_BITCOIN_TRANSACTION
                SIGN_ETHEREUM_TRANSACTIONS -> WalletContractV1.PURPOSE_SIGN_ETHEREUM_TRANSACTION
            }
        }

        companion object {
            fun fromWalletContractConstant(@WalletContractV1.Purpose c: Int): Purpose {
                return when (c) {
                    WalletContractV1.PURPOSE_SIGN_SOLANA_TRANSACTION -> SIGN_SOLANA_TRANSACTIONS
                    WalletContractV1.PURPOSE_SIGN_ALGORAND_TRANSACTION -> SIGN_ALGORAND_TRANSACTIONS
                    WalletContractV1.PURPOSE_SIGN_BITCOIN_TRANSACTION -> SIGN_BITCOIN_TRANSACTIONS
                    WalletContractV1.PURPOSE_SIGN_ETHEREUM_TRANSACTION -> SIGN_ETHEREUM_TRANSACTIONS
                    else -> throw IllegalArgumentException("Unknown purpose $c")
                }
            }
        }
    }

    companion object {
        const val INVALID_UID = Process.INVALID_UID
    }

    init {
        require(uid > INVALID_UID) { "UID is $uid; must be > $INVALID_UID" }
    }
}