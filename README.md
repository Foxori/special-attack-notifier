# Special Attack Notifier
![java_eEoa5yKmdh](https://github.com/user-attachments/assets/21839acd-a4f2-447d-88c4-c48c6d8457d2)
![kuva](https://github.com/user-attachments/assets/9860e33b-c2b5-44ea-a1ab-eb271bcc8ee6)

Displays info box alert at specified special attack threshold.
  - Configurable alert expiry time (in ticks)
  - Configurable color and flash between two colors

Displays info box alert before threshold is reached
  - Configurable expiry time (in ticks)
  - Configurable delay before threshold (in ticks)
  -  Configurable color and flash between two colors

Known issues:
  - Does not work correctly when special attack has decimals. For example adrenaline + DDS in ToA
  - Displays alert also when going special attack comes down from 100%
  - Alerts display wrong when changing special attack soon ticks
  - Investigate adding opacity to color
