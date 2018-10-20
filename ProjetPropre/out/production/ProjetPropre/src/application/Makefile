EDITEUR = atom

all:
	@echo Ce MakeFile vous permez de gérer le GIT du dépot \: projet
	@echo Si vous souhaitez cloner le dépôt faite \: make clone


add :
	@git add .

commit-% :
	@git add .
	@git commit -m "$(@:commit-%=%)"

diverge-% :
	@git checkout -b temp
	@git branch -f "$(@:diverge-%=%)" temp
	@git checkout "$(@:diverge-%=%)"
	@git branch -d temp

push :
	@git push

remote :
	@git remote -v

fetch_pers-% :
	@git fetch "$(@:fetch_pers-%=%)"
	@git merge "$(@:fetch_pers-%=%)"/master
	@git push

branch :
	@git branch -av

crea_branch-% :
	@git branch "$(@:crea_branch-%=%)"

mouv_branch-% :
	@git checkout "$(@:mouv_branch-%=%)"

supp_branch-% :
	@git branch -d "$(@:supp_branch-%=%)"

log :
	@git log

status :
	@git status

amont :
	@git push --set-upstream origin joueur

clone :
	@git clone https://gitlab.com/IUT45-2018/groupe42B.git

run :
	@$(EDITEUR) .
