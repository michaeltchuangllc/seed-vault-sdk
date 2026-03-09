/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solanamobile.fakewallet.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.solanamobile.fakewallet.usecase.SeedPurposeUseCase
import com.solanamobile.seedvault.WalletContractV1
import com.solanamobile.ui.apptheme.Sizes

@Composable
fun ChainSelector(
    selectedPurpose: Int,
    onPurposeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val purposes = listOf(
        WalletContractV1.PURPOSE_SIGN_SOLANA_TRANSACTION,
        WalletContractV1.PURPOSE_SIGN_ALGORAND_TRANSACTION
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Sizes.dp16, vertical = Sizes.dp8),
        horizontalArrangement = Arrangement.spacedBy(Sizes.dp8)
    ) {
        Text(
            text = "Chain:",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(end = Sizes.dp8)
                .align(androidx.compose.ui.Alignment.CenterVertically)
        )

        purposes.forEach { purpose ->
            FilterChip(
                selected = selectedPurpose == purpose,
                onClick = { onPurposeSelected(purpose) },
                label = { Text(SeedPurposeUseCase(purpose)) }
            )
        }
    }
}
