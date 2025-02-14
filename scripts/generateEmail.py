import os
#Générateur fichier .aut pour automate adresse mail car beaucoup de valeur ;)
alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
num="0123456789"
alphanum=alpha+num
lines=[]
lines.append("name: Email")
lines.append("description: ...")
lines.append("format: nom@domain.extension, ex: toto.re@gmail.com")
lines.append("initial state: E0")
lines.append("final states: E5")
for char in alphanum:
    lines.append(f"- E0 {char} E1")

for char in alphanum:
    lines.append(f"- E1 {char} E1")

lines.append("- E1 @ E2")
lines.append("- E1 . E0")

for char in alphanum:
    lines.append(f"- E2 {char} E3")

for char in alpha:
    lines.append(f"- E3 {char} E3")

lines.append("- E3 . E4")

for char in alpha:
    lines.append(f"- E4 {char} E5")

for char in alpha:
    lines.append(f"- E5 {char} E5")
lines.append(f"- E5 . E4")

#Save in File
script_dir = os.path.dirname(os.path.abspath(__file__))
os.makedirs(script_dir, exist_ok=True)
file_path = os.path.join(script_dir, "email.aut")
with open(file_path, "w") as f:
    f.write("\n".join(lines))