#install fonts
sudo mkdir ~/.local/share/fonts/Nunito
unzip src/fonts/Nunito.zip -d ~/.local/share/fonts/Nunito
fc-cache -f -v
